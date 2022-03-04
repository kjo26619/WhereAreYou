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
          agent any
          steps {
            dir ('./Backend/way'){
                sh """
                gradle tasks
                """
                sh """
                gradle build
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
          agent any
          steps {
            echo 'Bulid Docker'
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
          agent any
          steps {
            echo 'Push Docker'
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
