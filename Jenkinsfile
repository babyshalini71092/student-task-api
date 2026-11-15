
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                bat '.\\mvnw.cmd clean test'
            }
        }

        stage('Build') {
            steps {
                bat '.\\mvnw.cmd package -DskipTests'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true,
            testResults: 'target/surefire-reports/*.xml'
        }

        success {
            archiveArtifacts artifacts: 'target/*.jar',
            fingerprint: true
        }
    }
}
