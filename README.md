✅ One command start-up 

docker-compose up --build

✅ Example URL

curl -X POST "http://localhost:8080/shorten?url=https://google.com"

curl -X POST "http://localhost:8080/shorten?url=https://youtube.com"

❓ Expected response

http://localhost:8080/1a2c1a

❓ Reuse the generated Url

curl -v http://localhost:8080/abc123

curl -v http://localhost:8080/1a2c1a

✅ with TTL 10 seconds

curl -X POST "http://localhost:8080/shorten?url=https://gmail.com&ttlSeconds=10"
