# Import in Intellij

- New > Import project from existing sources > sbt project
- Maybe run `sbt clean` & `sbt compile`, because `sbt` is one strange thing



# Build Docker image

```
docker build .
docker run -e PLAY_SECRET_KEY=12dddddddd3 -e SECRET=123 -p 9000:9000 6952842f0bdd
```

For development use Docker Compose:

```
docker docker-compose up
```



# Docker Hub

- See https://hub.docker.com/r/sergsch/playkube



# Kubernetes

- Manifests were created via `kompose` (see [here](https://kubernetes.io/docs/tasks/configure-pod-container/translate-compose-kubernetes/))

  ```
  cd k8s
  kompose --file ../docker-compose.yml convert
  kubectl apply -f ./
  ```

- You will need to add `type: LoadBalancer` & `nodePort: 31001`

  ```
    type: LoadBalancer
    ports:
      - name: "9000"
        port: 9000
        targetPort: 9000
        nodePort: 31001
  ```

  