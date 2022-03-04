pipeline {
    agent any

    environment {
        imagename = "way-project"
        registryUrl = 'https://172.17.0.2:5000'
        registryCredential = 'JENKINS_REGISTRY_KEY'
        dockerImage = ''
    }

    stages {
        stage('Clonning Repository') {
            steps {
                checkout scm
            }
            
            post {
                success { 
                    echo 'Successfully Cloned Repository'
                }
                failure {
                    error 'Error Clonning Repository Stage'
                }
            }
        }
          
        stage('Bulid Gradle') {
            steps {
                dir ('./Backend/way'){
                    sh """
                    sudo ./gradlew tasks
                    """
                    sh """
                    sudo ./gradlew build
                    """
                }
            }
          
            post {
                success {
                    echo 'Successfully Build Gradle'
                }
                failure {
                    error 'Error Build Gradle Stage'
                }
            }
        }
        
        stage('Bulid Docker') {
            steps {
                script {
                    dockerImage = docker.build imagename
                }
            }
          
            post {
                success {
                    echo 'Successfully Build Docker'
                }
                failure {
                    error 'Error Build Docker Stage'
                }
            }
        }

        stage('Push Docker') {
            steps {
                script {
                    docker.withRegistry( registryUrl, registryCredential) {
                        dockerImage.push("1.0.0")
                    }
                }
            }
            
            post {
                success {
                    echo 'Successfully Push Docker'
                }
                failure {
                    error 'Error Push Docker Stage'
                }
            }
        }
    }
}
