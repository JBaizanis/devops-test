pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '30', artifactNumToKeepStr: '30'))
    }

    environment {
        EMAIL_TO = "it2021064@hua.gr"
        DOCKER_TOKEN = credentials('docker-push-secret')
        DOCKER_USER = 'jbaizanis'
        DOCKER_SERVER = 'ghcr.io'
        DOCKER_PREFIX = 'ghcr.io/jbaizanis/devops-spring' 
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'git@github.com:JBaizanis/docker-backend-repo.git'
            }
        }
        stage('Test') {
            steps {
                sh 'chmod +x ./mvnw && mvn package -Dmaven.test.skip'
            }
        }
        // stage('Docker build and push') {
        //     steps {
        //         sh '''
        //             HEAD_COMMIT=$(git rev-parse --short HEAD)
        //             TAG=$HEAD_COMMIT-$BUILD_ID
        //             docker build --rm -t $DOCKER_PREFIX:$TAG -t $DOCKER_PREFIX:latest  -f nonroot.Dockerfile .
        //             echo $DOCKER_TOKEN | docker login $DOCKER_SERVER -u $DOCKER_USER --password-stdin
        //             docker push $DOCKER_PREFIX --all-tags
        //         '''
        //     }
        // }
        stage('deploy to k8s') {
            steps {
                sh '''
                    #HEAD_COMMIT=$(git rev-parse --short HEAD)
                    #TAG=$HEAD_COMMIT-$BUILD_ID
                    #kubectl set image deployment/spring-deployment spring=$DOCKER_PREFIX:$TAG
                    #kubectl rollout status deployment spring-deployment --watch --timeout=2m
                    #kubectl create secret docker-registry registry-credentials --from-file=.dockerconfigjson=.dockerconfig.json
                    kubectl apply -f k8s/postgres/ 
                    kubectl apply -f k8s/spring/spring-svc.yaml
                    kubectl apply -f k8s/spring/spring-deployment.yaml
                    kubectl apply -f k8s/vue/deployment.yaml
                    kubectl apply -f k8s/vue/vue-svc.yaml
                    kubectl apply -f k8s/vue/vue-ingress.yaml
                '''
            }
        }

    }


}