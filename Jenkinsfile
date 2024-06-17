pipeline {
    agent any

    environment {
        EMAIL_TO = "it2021064@hua.gr"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'git@github.com:JBaizanis/docker-backend-repo.git'
            }
        }
        stage('Test') {
            steps {
                sh 'chmod +x ./mvnw && mvn package -Dmaven.test.skip'
            }
        }
        stage('run ansible pipeline') {
            steps {
                build job: 'ansible'
            }
        }
        stage('Install postgres') {
            steps {
                sh '''
                    export ANSIBLE_CONFIG=~/workspace/ansible/ansible.cfg
                    ansible-playbook -i ~/workspace/ansible/hosts.yaml -l azure-db-server ~/workspace/ansible/playbooks/postgres.yaml
                '''
            }
        }

        stage('Deploy spring boot app') {
            steps {
                sh '''
                   # replace dbserver in host_vars
                     sed -i 's/4.211.248.159/51.120.247.169/g' ~/workspace/ansible/host_vars/backend-vm.yaml
                   # replace workingdir in host_vars
                    # sed -i 's/vagrant/azureuser/g' ~/workspace/ansible/host_vars/appserver-vm.yaml
                '''
                sh '''
                    # edit host var for appserver

                    export ANSIBLE_CONFIG=~/workspace/ansible/ansible.cfg
                    ansible-playbook -i ~/workspace/ansible/hosts.yaml -l backend-vm ~/workspace/ansible/playbooks/spring.yaml
                '''
            }
        }
       stage('Deploy frontend') {
            steps {
                sh '''
                    sed -i 's/4.211.248.159/51.120.247.169/g' ~/workspace/ansible/host_vars/backend-vm.yaml
                    sed -i 's/40.89.179.118/51.120.247.169/g' ~/workspace/ansible/files/nginx.vue.j2
                    export ANSIBLE_CONFIG=~/workspace/ansible/ansible.cfg
                    ansible-playbook -i ~/workspace/ansible/hosts.yaml -l backend-vm -e branch=front -e backend_server_url=http://51.120.247.169:8080 ~/workspace/ansible/playbooks/vuejs-jenkins.yaml
                '''
            }
       }
    }
}