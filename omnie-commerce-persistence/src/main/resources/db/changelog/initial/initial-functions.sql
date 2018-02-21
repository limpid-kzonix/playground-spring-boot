CREATE OR REPLACE FUNCTION mark_service(IN id UUID)
  RETURNS VOID AS $$
DECLARE
  review_id UUID := (SELECT uuid
                     FROM mark_service
                     WHERE service_id = id);
BEGIN
  IF (review_id) ISNULL
  THEN
    INSERT INTO mark_service (uuid, service_id, rating) VALUES
      (DEFAULT, id, (SELECT round(avg(mark), 2)
                     FROM review_service
                     WHERE service_id = id AND accept_status = TRUE));
  ELSIF (review_id) NOTNULL
    THEN
      UPDATE mark_service
      SET rating = (SELECT round(avg(mark), 2)
                    FROM review_service
                    WHERE service_id = id AND accept_status = TRUE)
      WHERE uuid = review_id;
  END IF;
  EXCEPTION
  WHEN NOT_NULL_VIOLATION
    THEN
      RAISE NOTICE 'caught null for service mark';
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION mark_organization(IN id UUID)
  RETURNS VOID AS $$
DECLARE
  review_id UUID := (SELECT uuid
                     FROM mark_organization
                     WHERE organization_id = id);
BEGIN
  IF (review_id) ISNULL
  THEN
    INSERT INTO mark_organization (uuid, organization_id, rating)
    VALUES (DEFAULT, id, (SELECT round(avg(mark), 2)
                          FROM review_service
                            INNER JOIN service ON review_service.service_id = service.uuid
                          WHERE service.organization_id = id AND review_service.accept_status = TRUE));
  ELSIF (review_id) NOTNULL
    THEN
      UPDATE mark_organization
      SET rating = (SELECT round(avg(mark), 2)
                    FROM review_service
                      INNER JOIN service ON review_service.service_id = service.uuid
                    WHERE service.organization_id = id AND review_service.accept_status = TRUE)
      WHERE uuid = review_id;
  END IF;
  EXCEPTION
  WHEN NOT_NULL_VIOLATION
    THEN
      RAISE NOTICE 'caught null for organization mark';
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION datediff(units VARCHAR(30), start_t TIMESTAMP, end_t TIMESTAMP)
  RETURNS INT AS $$
DECLARE
  diff_interval INTERVAL;
  diff          INT = 0;
  years_diff    INT = 0;
BEGIN
  IF units IN ('yy', 'yyyy', 'year', 'mm', 'm', 'month')
  THEN
    years_diff = DATE_PART('year', end_t) - DATE_PART('year', start_t);

    IF units IN ('yy', 'yyyy', 'year')
    THEN
      -- SQL Server does not count full years passed (only difference between year parts)
      RETURN years_diff;
    ELSE
      -- If end month is less than start month it will subtracted
      RETURN years_diff * 12 + (DATE_PART('month', end_t) - DATE_PART('month', start_t));
    END IF;
  END IF;

  -- Minus operator returns interval 'DDD days HH:MI:SS'
  diff_interval = end_t - start_t;

  diff = diff + DATE_PART('day', diff_interval);

  IF units IN ('wk', 'ww', 'week')
  THEN
    diff = diff / 7;
    RETURN diff;
  END IF;

  IF units IN ('dd', 'd', 'day')
  THEN
    RETURN diff;
  END IF;

  diff = diff * 24 + DATE_PART('hour', diff_interval);

  IF units IN ('hh', 'hour')
  THEN
    RETURN diff;
  END IF;

  diff = diff * 60 + DATE_PART('minute', diff_interval);

  IF units IN ('mi', 'n', 'minute')
  THEN
    RETURN diff;
  END IF;

  diff = diff * 60 + DATE_PART('second', diff_interval);

  RETURN diff :: INT;
END;
$$ LANGUAGE plpgsql;
COMMIT;
