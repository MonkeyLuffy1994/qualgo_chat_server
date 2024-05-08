## Run Project.
Use docker run command:
- `docker-compose up`
- `server run port: 8080`
## Run unit test.

#### Call curl below or import file postman in package document.
- Api register account.
  ``curl --location 'localhost:8080/register' \
  --header 'Content-Type: application/json' \
  --data '{
  "userName": "user_1",
  "passWord": "123456"
  }'``
- Api login.
  ``curl --location 'localhost:8080/login' \
  --header 'Content-Type: application/json' \
  --data '{
  "userName": "user_1",
  "passWord": "123456"
  }'``
- Send message to WebSocket with postman: ws://localhost:8080/sendMessage and connect.
- Api getListMessageByUser.
  ``curl --location 'localhost:8080/get-message-by-user' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzE1MTY0MTQ1LCJleHAiOjE3MTUxNjcxNDV9.Q3C_bfxVsq0aard72SsmQdIlBUBcv4635eNlbR7JXwM'``
- Api getAllMessageInRoom.
  ``curl --location 'localhost:8080/get-all-message-in-room' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzE1MTY0MTQ1LCJleHAiOjE3MTUxNjcxNDV9.Q3C_bfxVsq0aard72SsmQdIlBUBcv4635eNlbR7JXwM'``
- Api delete message.
  ``curl --location --request DELETE 'localhost:8080/delete-message/3' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzE1MTY0MTQ1LCJleHAiOjE3MTUxNjcxNDV9.Q3C_bfxVsq0aard72SsmQdIlBUBcv4635eNlbR7JXwM'``

*** Note project used Jwt to authentication, Please get new token when login to authentication.
With webSocket, Please add token into header with format below.
"Authorization": "Bearer " + (token received when login success.)
