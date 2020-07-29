###delete
`curl -X DELETE http://localhost:8080/topjava/rest/meals/100002`

###update
`curl -X PUT -H "Content-Type: application/json" -d '{"id":100002,"dateTime":"2020-01-30T10:00:00","description":"Обновлённый завтрак","calories":200}' http://localhost:8080/topjava/rest/meals/100002`

###create
`curl -H "Content-Type: application/json" -d '{"dateTime":"2020-01-31T18:00:00","description":"Новый завтрак","calories":200}' http://localhost:8080/topjava/rest/meals`

###getBetween
`curl 'http://localhost:8080/topjava/rest/meals/filter?fromDate=2020-01-30&toDate=2020-01-30'`

###getAll
`curl http://localhost:8080/topjava/rest/meals`

###get
`curl http://localhost:8080/topjava/rest/meals/100002`