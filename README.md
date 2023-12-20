# requirements
- needs a local llama2 that can be downloaded from https://ollama.ai/
- after download poke in "ollama run llama2", this will automatically download the smallest llama2 and open a chat console
- launch Application Class
- also has an example for remote chatgpt with the demo key

# ollama docker (3x slower due to missing m1 gpu support)
docker run --rm -v ~/.ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama:0.1.13
docker exec -it ollama ollama run llama2

# curl
time curl http://localhost:11434/api/generate -d '{
"model": "llama2",
"prompt": "How are you doing ?"
}'

# links
https://github.com/langchain4j/langchain4j
https://blog.gopenai.com/langchain4j-supercharge-your-java-application-with-the-power-of-ai-6802e4237094
https://sych.io/blog/how-to-run-llama-2-locally-a-guide-to-running-your-own-chatgpt-like-large-language-model/
                      
# ollama links
https://github.com/jmorganca/ollama/blob/main/docs/api.md
https://hub.docker.com/r/ollama/ollama
https://github.com/jmorganca/ollama

# localai
https://localai.io/basics/getting_started/

./local-ai-avx-Darwin-x86_64 --models-path ~/.localai/models

curl http://localhost:8080/v1/models

curl -v http://localhost:8080/v1/completions -H "Content-Type: application/json" -d '{
"model": "GPT4All-J Groovy.bin",
"prompt": "A long time ago in a galaxy far, far away",
"temperature": 0.7
}'
                            
