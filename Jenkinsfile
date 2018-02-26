pipeline {
    agent any

    tools { 
        maven 'maven_3.5.0' 
    }
    stages {
        stage ('Initialize') {
            steps {
                git([url: 'https://github.com/ShwetaAKulkarni/expense-manager'])
                //def mvnHome = tool 'maven_3.3.9'
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                ''' 
            }
        }

        stage ('Build') {

            steps {
                echo 'This is build phase.'
                sh "mvn clean compile"
            }
        }

        stage ('Test') {

            steps {
                echo 'This is test phase.'
				  sh "${M2_HOME}/bin/mvn clean test"
            }
        }

        stage ('Package') {

            steps {
                echo 'This is package phase.'
                sh "${M2_HOME}/bin/mvn clean package -Dmaven.test.skip=true"
            }
        }
        
        stage ('Deploy') {
            steps {
                echo 'This is deploy phase.'
                sh "rm -rf /tmp/exp/*"
                sh "cp /Users/shweta/.jenkins/workspace/expense-manager/exp-account-service/target/exp-account-service-0.0.1-SNAPSHOT-distribution.zip /tmp/exp/"
                sh "cp /Users/shweta/.jenkins/workspace/expense-manager/exp-client/target/exp-client-0.0.1-SNAPSHOT-distribution.zip /tmp/exp/"
                sh "unzip -o /tmp/exp/exp-account-service-0.0.1-SNAPSHOT-distribution.zip -d /tmp/exp/"
                sh "unzip -o /tmp/exp/exp-client-0.0.1-SNAPSHOT-distribution.zip -d /tmp/exp/"
                sh "chmod -R +x /tmp/exp/exp-*/run*.sh"
            }
        }
        
        stage ('Run') {
            steps {
                echo 'This is run phase.'
                sh "cd /tmp/exp"
                sh "nohup /tmp/exp/exp-account-service-0.0.1-SNAPSHOT/run-account-service.sh > /tmp/service.log 2>&1 &"
                sh "nohup /tmp/exp/exp-client-0.0.1-SNAPSHOT/run-client.sh > /tmp/client.log 2>&1 &"
            }
        }

    }
}
