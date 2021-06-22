pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'Java JDK'
        dockerTool 'Docker'
    }
    stages {
        stage('Clean and Test target') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Test and Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Code Analysis: Sonarqube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Await Quality Gateway') {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
        stage('Dockerize') {
            steps {
                script {
                    docker.build('accountservice')
                }
            }
        }
        stage('Push ECR') {
            steps {
                script {
                    docker.withRegistry('https://241465518750.dkr.ecr.us-east-2.amazonaws.com', 'ecr:us-east-2:aws-ecr-creds') {
                        docker.image('accountservice').push("${env.BUILD_NUMBER}")
                        docker.image('accountservice').push('latest')
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying cloudformation..'
                withKubeConfig([credentialsId: 'eks-config', serverUrl: 'https://C26AA669C29F29D1DD2464FFE44053D3.sk1.us-east-2.eks.amazonaws.com']) {
                    sh 'kubectl set image https://241465518750.dkr.ecr.us-east-2.amazonaws.com/accountservice:latest'
                }
            }
        }
    }
    post {
        always {
            sh 'mvn clean'
        }
    }
}