echo Start server ...
start cmd /k newbuild.bat

TIMEOUT 30

echo Post test request
call curl -X POST -H "Content-type: application/json" --data "{\"targetText\": \"lorem ipsum\", \"alphabet\": \"lorem ipsum\", \"persistanceThreshold\": 2}" http://localhost:8080/run/start