# Routes

Note that the main route is:
```
/playkube
```

# Import in Intellij

- New > Import project from existing sources > sbt project
- Maybe run `sbt clean` & `sbt compile`, because `sbt` is one strange thing



# Build Docker image

```
docker build . --tag sergsch/playkube:latest
```

For development use Docker Compose:

```
docker docker-compose up
```

Or directly with something like that:
```
docker run -e PLAY_SECRET_KEY=12dddddddd3 -e SECRET=123 -p 9000:9000 6952842f0bdd
```



# Push to Docker Hub

- See https://hub.docker.com/r/sergsch/playkube

```
docker login
docker push sergsch/playkube:latest
```


# Volumes

- Uses a named volume, see `docker-compose.yml`

- Docker will automatically copy the content from `/app/conf` into the volume folder it created. 

  ```yaml
  # ...
      volumes:
          - conf_dir:/app/conf
  
  volumes:
    conf_dir: {}
  ```

- Find path via (see "Mountpoint"):

  ```bash
  docker volume ls
  docker volume inspect playkube_conf_dir
  ```

- You can make a bind mount out of that via:

  ```yaml
  # ...
      volumes:
          - /home/cc/Desktop/_trash/playkube_volume/conf_dir:/app/conf
  # no `volumes:` block afterwards
  ```

  But keep in mind that Docker will not copy any content. 

  - If the path does not exist then Docker will create an empty folder and mount it into the container, i.e. `/app/conf` will be an empty folder in the container.
  - If the path exists, then Docker will mount the folder into the container. All files available in the folder are accessible in the container. 



# Kubernetes

- Manifests were created via `kompose` (see [here](https://kubernetes.io/docs/tasks/configure-pod-container/translate-compose-kubernetes/))

  ```
  cd k8s
  kompose --file ../docker-compose.yml convert
  kubectl apply -f ./
  ```

- You will need to add `type: NodePort` & `nodePort: 31001`

  ```
    type: NodePort
    ports:
      - name: "9000"
        port: 9000
        targetPort: 9000
        nodePort: 31001
  ```

