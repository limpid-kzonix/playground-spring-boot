# Security Module

## Authorization


### request
  `method` - `POST`

  `path` - http://host:9005/omnie-security/oauth/token

  `request-params` :
    - `grant_type` - `password`
    - `username` - your username
    - `password` - your password

  `headers` :
    - `Authorization` : Basic Authorization (encoded pair of client and secret)
      + example - `Basic ` + `base64Encode(web-client:secret)` 


*curl* example

```bash
        curl --request POST \
          --url 'http://omniecom.com:9999/omnie-security/api/oauth/token?grant_type=password&username=alexander&password=new_password' \
          --header 'authorization: Basic d2ViLWNsaWVudDpzZWNyZXQ='
```
    > where header 'Authorization' = Basic Authorization with pair = 'web-client:secret'

### response
*json*

```json
  {
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhbGV4YW5kZXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdLCJyb2xlcyI6W3siaWQiOiJlNmJhZTM1MC02ZDE3LTQwOTMtOWEyYi00MmNkMTY3Njc0ODMiLCJuYW1lIjoiUk9MRV9VU0VSIn1dLCJvbW5pZWNhcmQiOiJvbW5pZSMxMDA0ODYwOSIsInVzZXJFbWFpbCI6ImJhbHlzenluQGdtYWlsLmNvbSIsImV4cCI6MTUxMzA3OTQzNCwidXNlcklkIjoiMGI3NTJhNDQtNWFkNC00ZjVhLTljNzAtY2Q1ODRhNmRlZmZiIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImY0ZGEzNWVhLTc2YzEtNDhiZi05ZGQ0LWUxNDcwZTQ3ZWM2ZiIsImNsaWVudF9pZCI6IndlYi1jbGllbnQifQ.T22Z-dsQeTkQ2XYRfc8kGPBNrzWokNGpRRsav5fq67euj4ICSg1B-uZoFBHAXfYk6kr8DhbVD43ls9HBFEOo4Tf6l-WLA6q-9e14i89Eft7nU3SmcEcBAmG5UEwBrc26-_yDhgvPH6-1T8yU-Vs3Zp-E3GyuO8Qxjrqvq6uyjZHT39lHDDM1W2mc2Snzz2NT4GNOupSZ8rAGaLl8W44L2SAnonVjEYC7d1J14Qug4Mh4fO-eSGUi68u6nfCfkq4m7rqMVKjrd8fNduwPKnkMhB3pqwKaXHBg_h3kop9f0O24d8lSSYqBRTe0ghxToftXldqgislIigYb2uKMyKtLXA",
    "token_type": "bearer",
    "expires_in": 604799,
    "scope": "read write trust",
    "roles": [
      {
        "id": "e6bae350-6d17-4093-9a2b-42cd16767483",
        "name": "ROLE_USER"
      }
    ],
    "omniecard": "omnie#10048609",
    "userEmail": "balyszyn@gmail.com",
    "userId": "0b752a44-5ad4-4f5a-9c70-cd584a6deffb",
    "jti": "f4da35ea-76c1-48bf-9dd4-e1470e47ec6f"
  }
```

if User account has not profile data response message has structure:
```json
{
	"access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleGNlcHRpb24iOlt7ImtleSI6InByb2ZpbGUiLCJjb2RlIjoyMjAxLCJ2YWx1ZSI6Im5lZWQgdG8gZmlsbCBwcm9maWxlIn1dLCJ1c2VyX25hbWUiOiJhbGV4YW5kZXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdLCJyb2xlcyI6W3siaWQiOiJlNmJhZTM1MC02ZDE3LTQwOTMtOWEyYi00MmNkMTY3Njc0ODMiLCJuYW1lIjoiUk9MRV9VU0VSIn1dLCJ1c2VyRW1haWwiOiJiYWx5c3p5bkBnbWFpbC5jb20iLCJleHAiOjE1MTQ0NjkwMDksInVzZXJJZCI6IjBiNzUyYTQ0LTVhZDQtNGY1YS05YzcwLWNkNTg0YTZkZWZmYiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIxODI5Yzg3Zi02N2M1LTQwYzMtODg4Ni1hYzFmNWY2NTEzNTQiLCJjbGllbnRfaWQiOiJ3ZWItY2xpZW50In0.AZOAOjz2F9pvBSrNfEDrddNOiWExEtgeN6J2Yc3ig8v1WE9EbAB41-jPUMtOzCEnCgr-qN3B-puoHqU3nAW1V1gMwmkjogos103RsnD1di4N8hviGklG7dqL_My8jyit2l6_Qz-TJMgR3sGeh-FANW_5z7GDjswk0Nm-eikIclAHjVQc_pHDiwUGs59KUFlxwewV5ba6S8PzZnjENCj5Jp13iOEYaT5uIdqwz044ARoNyPQDPw7OTQJd2rOVcGZgV8r6KjIFdAhE-a8WwzEjmtnugJni4NwvMZzWdAaHMtUwn0Z1dOI2M1u-SbITt38Buz87vTlLXbhmZyNeBp0CHg",
	"token_type": "bearer",
	"expires_in": 604799,
	"scope": "read write trust",
	"exception": [
		{
			"key": "profile",
			"code": 2201,
			"value": "need to fill profile"
		}
	],
	"roles": [
		{
			"id": "e6bae350-6d17-4093-9a2b-42cd16767483",
			"name": "ROLE_USER"
		}
	],
	"userEmail": "balyszyn@gmail.com",
	"userId": "0b752a44-5ad4-4f5a-9c70-cd584a6deffb",
	"jti": "1829c87f-67c5-40c3-8886-ac1f5f651354"
}

```

> Use this token for all secured request
`Authorization : Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhbGV4YW5kZXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdLCJyb2xlcyI6W3siaWQiOiJlNmJhZTM1MC02ZDE3LTQwOTMtOWEyYi00MmNkMTY3Njc0ODMiLCJuYW1lIjoiUk9MRV9VU0VSIn1dLCJvbW5pZWNhcmQiOiJvbW5pZSMxMDA0ODYwOSIsInVzZXJFbWFpbCI6ImJhbHlzenluQGdtYWlsLmNvbSIsImV4cCI6MTUxMzA3OTQzNCwidXNlcklkIjoiMGI3NTJhNDQtNWFkNC00ZjVhLTljNzAtY2Q1ODRhNmRlZmZiIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImY0ZGEzNWVhLTc2YzEtNDhiZi05ZGQ0LWUxNDcwZTQ3ZWM2ZiIsImNsaWVudF9pZCI6IndlYi1jbGllbnQifQ.T22Z-dsQeTkQ2XYRfc8kGPBNrzWokNGpRRsav5fq67euj4ICSg1B-uZoFBHAXfYk6kr8DhbVD43ls9HBFEOo4Tf6l-WLA6q-9e14i89Eft7nU3SmcEcBAmG5UEwBrc26-_yDhgvPH6-1T8yU-Vs3Zp-E3GyuO8Qxjrqvq6uyjZHT39lHDDM1W2mc2Snzz2NT4GNOupSZ8rAGaLl8W44L2SAnonVjEYC7d1J14Qug4Mh4fO-eSGUi68u6nfCfkq4m7rqMVKjrd8fNduwPKnkMhB3pqwKaXHBg_h3kop9f0O24d8lSSYqBRTe0ghxToftXldqgislIigYb2uKMyKtLXA`

## Refresh Token


### request
  `method` - `POST`

  `path` - http://host:9005/omnie-security/oauth/token

  `request-params` :
    - `grant_type` - `refresh_token`
    - `refresh_token` - your refresh token

  `headers` :
    - `Authorization` : Basic Authorization (encoded pair of client and secret)
      + example - `Basic ` + `base64Encode(web-client:secret)` 


*curl* example

```bash
        curl --request POST           --url 'http://omniecom.com:9999/omnie-security/api/oauth/token?grant_type=refresh_token&refresh_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleGNlcHRpb24iOlt7ImtleSI6InByb2ZpbGUiLCJjb2RlIjoyMjAxLCJ2YWx1ZSI6Im5lZWQgdG8gZmlsbCBwcm9maWxlIn1dLCJ1c2VyX25hbWUiOiJvbGVrc2FuZHIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdLCJyb2xlcyI6W3siaWQiOiJkOTE3NWQ3Ni0xYWJmLTRkOTktYTJkNS0xYmRiZmUwNjBkODMiLCJuYW1lIjoiUk9MRV9VU0VSIn1dLCJhdGkiOiIxYTJlZWQ3MC02ZTk2LTQ0ZDAtODMxNi03YWUwM2NjNTUxODEiLCJ1c2VyRW1haWwiOiJhbGV4YW5kZXIuYmFseXNoeW5AeWFob28uY29tIiwiZXhwIjoxNTIyNTc2NzYzLCJ1c2VySWQiOiJlMjVkMGQ2ZS00ZDk4LTRmY2ItOWU3Zi0yZDk1MTgwY2I0N2QiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMzY3OGI1ZGYtZmQ5My00NTg2LWI2NTEtM2FiMmRiNWQ1MDk3IiwiY2xpZW50X2lkIjoid2ViLWNsaWVudCJ9.WV4n7g3OvHnu6yE6DA76fie1iYqrORDg-lil8wxxZRPmc-4xkFx88Pryn_KClO9spaI61Hvxs1b3e21bqzigiz5u9SddaTmVHz0WnEFfzlLAWqYtbDmz_qcT_mX5lKUKoHaRmkjTDOpkGlzTgwp_4cDLjBZwI_nJA31c-JJFE-87htSYAECKhmpJgz2ATqiPtKlQiHRo147tVdgUngCNIHjOyiDc9YaPtszJ4rx_18USAeT4yA3yq_OpqjAdAXdwG0TQ7RLuZ0Dd1NZtBkFtWz9Nm6WT8oBfhgxmDqKebixSLOquzxUJbPV4EwDJ9u7CdN5I_OdOqzL31rrsSV25sw'           --header 'authorization: Basic d2ViLWNsaWVudDpzZWNyZXQ='

```
    > where header 'Authorization' = Basic Authorization with pair = 'web-client:secret'

### response
*json*

```json
  {
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhbGV4YW5kZXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdLCJyb2xlcyI6W3siaWQiOiJlNmJhZTM1MC02ZDE3LTQwOTMtOWEyYi00MmNkMTY3Njc0ODMiLCJuYW1lIjoiUk9MRV9VU0VSIn1dLCJvbW5pZWNhcmQiOiJvbW5pZSMxMDA0ODYwOSIsInVzZXJFbWFpbCI6ImJhbHlzenluQGdtYWlsLmNvbSIsImV4cCI6MTUxMzA3OTQzNCwidXNlcklkIjoiMGI3NTJhNDQtNWFkNC00ZjVhLTljNzAtY2Q1ODRhNmRlZmZiIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImY0ZGEzNWVhLTc2YzEtNDhiZi05ZGQ0LWUxNDcwZTQ3ZWM2ZiIsImNsaWVudF9pZCI6IndlYi1jbGllbnQifQ.T22Z-dsQeTkQ2XYRfc8kGPBNrzWokNGpRRsav5fq67euj4ICSg1B-uZoFBHAXfYk6kr8DhbVD43ls9HBFEOo4Tf6l-WLA6q-9e14i89Eft7nU3SmcEcBAmG5UEwBrc26-_yDhgvPH6-1T8yU-Vs3Zp-E3GyuO8Qxjrqvq6uyjZHT39lHDDM1W2mc2Snzz2NT4GNOupSZ8rAGaLl8W44L2SAnonVjEYC7d1J14Qug4Mh4fO-eSGUi68u6nfCfkq4m7rqMVKjrd8fNduwPKnkMhB3pqwKaXHBg_h3kop9f0O24d8lSSYqBRTe0ghxToftXldqgislIigYb2uKMyKtLXA",
    "token_type": "bearer",
    "expires_in": 604799,
    "scope": "read write trust",
    "roles": [
      {
        "id": "e6bae350-6d17-4093-9a2b-42cd16767483",
        "name": "ROLE_USER"
      }
    ],
    "omniecard": "omnie#10048609",
    "userEmail": "balyszyn@gmail.com",
    "userId": "0b752a44-5ad4-4f5a-9c70-cd584a6deffb",
    "jti": "f4da35ea-76c1-48bf-9dd4-e1470e47ec6f"
  }
```

if User account has not profile data response message has structure:
```json
{
	"access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleGNlcHRpb24iOlt7ImtleSI6InByb2ZpbGUiLCJjb2RlIjoyMjAxLCJ2YWx1ZSI6Im5lZWQgdG8gZmlsbCBwcm9maWxlIn1dLCJ1c2VyX25hbWUiOiJhbGV4YW5kZXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdLCJyb2xlcyI6W3siaWQiOiJlNmJhZTM1MC02ZDE3LTQwOTMtOWEyYi00MmNkMTY3Njc0ODMiLCJuYW1lIjoiUk9MRV9VU0VSIn1dLCJ1c2VyRW1haWwiOiJiYWx5c3p5bkBnbWFpbC5jb20iLCJleHAiOjE1MTQ0NjkwMDksInVzZXJJZCI6IjBiNzUyYTQ0LTVhZDQtNGY1YS05YzcwLWNkNTg0YTZkZWZmYiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIxODI5Yzg3Zi02N2M1LTQwYzMtODg4Ni1hYzFmNWY2NTEzNTQiLCJjbGllbnRfaWQiOiJ3ZWItY2xpZW50In0.AZOAOjz2F9pvBSrNfEDrddNOiWExEtgeN6J2Yc3ig8v1WE9EbAB41-jPUMtOzCEnCgr-qN3B-puoHqU3nAW1V1gMwmkjogos103RsnD1di4N8hviGklG7dqL_My8jyit2l6_Qz-TJMgR3sGeh-FANW_5z7GDjswk0Nm-eikIclAHjVQc_pHDiwUGs59KUFlxwewV5ba6S8PzZnjENCj5Jp13iOEYaT5uIdqwz044ARoNyPQDPw7OTQJd2rOVcGZgV8r6KjIFdAhE-a8WwzEjmtnugJni4NwvMZzWdAaHMtUwn0Z1dOI2M1u-SbITt38Buz87vTlLXbhmZyNeBp0CHg",
	"token_type": "bearer",
	"expires_in": 604799,
	"scope": "read write trust",
	"exception": [
		{
			"key": "profile",
			"code": 2201,
			"value": "need to fill profile"
		}
	],
	"roles": [
		{
			"id": "e6bae350-6d17-4093-9a2b-42cd16767483",
			"name": "ROLE_USER"
		}
	],
	"userEmail": "balyszyn@gmail.com",
	"userId": "0b752a44-5ad4-4f5a-9c70-cd584a6deffb",
	"jti": "1829c87f-67c5-40c3-8886-ac1f5f651354"
}

```

> Use this token for all secured request
`Authorization : Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhbGV4YW5kZXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdLCJyb2xlcyI6W3siaWQiOiJlNmJhZTM1MC02ZDE3LTQwOTMtOWEyYi00MmNkMTY3Njc0ODMiLCJuYW1lIjoiUk9MRV9VU0VSIn1dLCJvbW5pZWNhcmQiOiJvbW5pZSMxMDA0ODYwOSIsInVzZXJFbWFpbCI6ImJhbHlzenluQGdtYWlsLmNvbSIsImV4cCI6MTUxMzA3OTQzNCwidXNlcklkIjoiMGI3NTJhNDQtNWFkNC00ZjVhLTljNzAtY2Q1ODRhNmRlZmZiIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImY0ZGEzNWVhLTc2YzEtNDhiZi05ZGQ0LWUxNDcwZTQ3ZWM2ZiIsImNsaWVudF9pZCI6IndlYi1jbGllbnQifQ.T22Z-dsQeTkQ2XYRfc8kGPBNrzWokNGpRRsav5fq67euj4ICSg1B-uZoFBHAXfYk6kr8DhbVD43ls9HBFEOo4Tf6l-WLA6q-9e14i89Eft7nU3SmcEcBAmG5UEwBrc26-_yDhgvPH6-1T8yU-Vs3Zp-E3GyuO8Qxjrqvq6uyjZHT39lHDDM1W2mc2Snzz2NT4GNOupSZ8rAGaLl8W44L2SAnonVjEYC7d1J14Qug4Mh4fO-eSGUi68u6nfCfkq4m7rqMVKjrd8fNduwPKnkMhB3pqwKaXHBg_h3kop9f0O24d8lSSYqBRTe0ghxToftXldqgislIigYb2uKMyKtLXA`

# OAuth2.0 Specification
### Unified error message 
##### It may occur when performing requests to Resources Servers with the wrong token or its absence.

*The OAuth 2.0 [specification](https://tools.ietf.org/html/rfc6749) is very clear on the errors returned by the authorization server in [Section 5.2. Error Response](https://tools.ietf.org/html/rfc6749#section-5.2) :

```json
    error : REQUIRED.
    error_description : OPTIONAL
```
