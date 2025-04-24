✅ One command start-up 

docker-compose up --build

✅ Example URL

curl -X POST "http://localhost:8080/shorten?url=https://google.com"

❓Expected response

http://localhost:8080/abc123

❓Reuse the generated Url

curl -v http://localhost:8080/abc123

❓with TTL 10 seconds 

curl -X POST "http://localhost:8080/shorten?url=https://example.com&ttlSeconds=10"
