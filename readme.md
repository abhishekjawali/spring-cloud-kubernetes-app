# Spring Cloud Kubernetes - Sample application

This is a reference application which shows how Spring Cloud Kubernetes makes it easy for us consume native Kubernetes components from Spring boot application.

The following features of Spring Cloud Kubernetes are implemented in this application: 
1. Kubernetes native service discovery
2. Using config maps to get application properties
3. Using secrets for consuming sensitive information
4. Auto reload of properties from config maps
5. RBAC configuration to enable application to consume Kubernetes API services
6. [Jib](https://github.com/GoogleContainerTools/jib) for creating docker images 

To Do:
1. Using service discovery ( I did not find much difference or advantages of using Spring cloud kubernetes service discovery as compared to using native service discovery provided by kubernetes.)
2. Config refresh

## Design
### Product Service application
1. This application has REST API exposed to get the list of products and also to fetch the product with its ID.
2. This application is using MySQL DB, where all product details are persisted.
3. The DML scripts are loaded at app startup. 
4. Application and MySQL are Dockerized. MySQL is not using PVC. The data is in memory. 


## Running the application
### Checkout the code
```
git checkout https://github.com/abhishekjawali/spring-cloud-kubernetes-app.git
cd product-service
```

### Build the application and push to Dockerhub
```
mvn clean install
export PRODUCT_SERVICE_TAG=abhishekjv/product-service
mvn compile jib:build
```
Replace the value of 'PRODUCT_SERVICE_TAG' to your own Docker hub repo.


### Setup RBAC in Kubernetes
```
kubectl apply -f 1-cluster-role.yaml
```

### Deploy MySQL DB
MySQL DB username and password are encoded and stored as Secrets in Kubernetes. 
Here, mysql:5.6 image is used to spin up the DB.
For reference, 
- Username and password are set as '*abhi*'. 
- DB name is '*testdb*'
- URI will be *'jdbc:mysql://reference-app-mysql:3306/testdb?useSSL=false'*
```
kubectl apply -f 2-mysql.yaml
```

### Deploy the application
- When deploying to Kubernetes, app is configured to use the profile 'kubernetes'
- Username and password are injected to the application as environment variables. These are read from Secrets.
    ```
    containers:
        - name: product-app
          image: abhishekjv/product-service:v3
          imagePullPolicy: Always
          env:
            - name: SPRING_PROFILES_ACTIVE
              value: kubernetes
            - name: SPRING_DATASOURCE_USERNAME
              valueFrom:
                secretKeyRef:
                  name: mysql-user-pass
                  key: username
            - name: SPRING_DATASOURCE_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: mysql-user-pass
                  key: password
            - name: SPRING_DATASOURCE_URL
              valueFrom:
                configMapKeyRef:
                  name: product-service
                  key: mysql.db.url
    ```
- URI to connect to database is stored in ConfigMap and injected to the application. 
    ```
    apiVersion: v1
    kind: ConfigMap
    data:
      mysql.db.url: jdbc:mysql://reference-app-mysql:3306/testdb?useSSL=false
      product.message: This is from ConfigMap!
    metadata:
      name: product-service
    ```
```
kubectl apply -f 3-application.yaml
```

The service is exposed on NodePort *30080*

### Test the application
1. If running in minikube, get he minikube IP
    ```
    minikube ip
    ```
    
2. Hit the API to get the product detail stored in DB. 
    ```
    curl <MINIKUBE-IP>:30080/products
    ```

