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
                     WHERE service_id = id AND accept_status = TRUE
                     GROUP BY uuid, mark));
  ELSIF (review_id) NOTNULL
    THEN
      UPDATE mark_service
      SET rating = (SELECT round(avg(mark), 2)
                    FROM review_service
                    WHERE service_id = id AND accept_status = TRUE
                    GROUP BY uuid, mark)
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
    VALUES (DEFAULT, id, (SELECT ((round(avg(review_service.mark), 2) + round(avg(review_organization.mark), 2)) / 2)
                            FROM review_service
                              INNER JOIN service ON review_service.service_id = service.uuid
                              INNER JOIN review_organization ON review_organization.organization_id = id
                            WHERE service.organization_id = id AND review_service.accept_status = TRUE
                            GROUP BY review_organization.mark, review_service.mark)
                          );
  ELSIF (review_id) NOTNULL
    THEN
      UPDATE mark_organization
      SET rating = (SELECT ((round(avg(review_service.mark), 2) + round(avg(review_organization.mark), 2)) / 2)
                    FROM review_service
                      INNER JOIN service ON review_service.service_id = service.uuid
                      INNER JOIN review_organization ON review_organization.organization_id = id
                    WHERE service.organization_id = id AND review_service.accept_status = TRUE
                    GROUP BY review_organization.mark, review_service.mark)
      WHERE uuid = review_id;
  END IF;
  EXCEPTION
  WHEN NOT_NULL_VIOLATION
    THEN
      RAISE NOTICE 'caught null for organization mark';
END;
$$ LANGUAGE plpgsql;

