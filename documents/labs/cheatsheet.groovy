// Tell jenkins to run the code on a specific node
node("Node_name"){

}

// Tell Jenkins to use any node
//More info: https://www.jenkins.io/doc/book/pipeline/#scripted-pipeline-fundamentals
node{

}

// Workspace, tell Jenkins which directory to put files. File name can not contain whitespaces. 
//if we have nodes with multiple executors, we want to separate the workspaces so the executors don't interfere with eachother
// In our jobs we set this to "/var/jenkins/workspace_${env.EXECUTOR_NUMBER}"
// More info: https://www.jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#ws-allocate-workspace
ws("/var/jenkins/workspace_${env.EXECUTOR_NUMBER}")

// Useful way to visualize the different parts of the job in stages, the stages are shown in the job page when a job has been run
// More info: https://www.jenkins.io/doc/book/pipeline/#scripted-pipeline-fundamentals
stage("stageName")

// Set which Git repo to clone and use. More info:  https://www.jenkins.io/doc/pipeline/steps/git/
git(branch:"main", url:'https://github.com/dev4242/project-x.git')

// Run a bash command 
//More info: https://www.jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#sh-shell-script
sh("echo apa")

// Clean the workspace, removes all files, takes no parameters
// More info: https://www.jenkins.io/doc/pipeline/steps/ws-cleanup/
cleanWs()

// Start another job in the Jenkins machinery 
// More info: https://www.jenkins.io/doc/pipeline/steps/pipeline-build-step/
build(job:"JOB_NAME",
                  propagate:true,
                  wait:true,
                  parameters:[[$class:'StringParameterValue', name:"STRING_PARAMETER", value:"YES"],
                             booleanParam(name:"BOOLEAN_PARAM", value: true)
                      ]
                
                )

// Checkout a pull request. This one is tough. 
// Branches defins the branch to fetch
// $GITHUB_PR_NUMBER is the pull request number
// userRemoteConfigs is making sure we can fetch the pull requests
// $GITHUB_REPO_GIT_URL will point to the configured URL
checkout([$class: 'GitSCM',
                    branches: [
                        [
                            name: "pr/$GITHUB_PR_NUMBER"
                        ]
                    ],
                    userRemoteConfigs: [
                        [
                            name: 'pr',
                            refspec: "+refs/pull/*/head:refs/remotes/pr/*",
                            url: "${GITHUB_REPO_GIT_URL}"]
                        ]
                    ]
                )

// Print text to stdout
println("APA")
// Get environment variables. All parameters are inserted as environment variable
env.VARIABLE

// Run code in parallel in scripted pipeline 
// Documentation for declarative syntax: https://www.jenkins.io/doc/book/pipeline/syntax/#parallel
def toBeRunParallel(id){
     return {
        node{
            ws("/var/jenkins/workspace_${env.EXECUTOR_NUMBER}"){
                println("apan sover")
            }
            
        }
    }
}
node{
    stage("build"){
        def test1 = toBeRunParallel("1")
        def test2 =  toBeRunParallel("2")
        def tests = [:]
        tests["test1"] = test1
        tests["test2"] = test2
        parallel tests
    }
}



// Create variable of any type, "def" is a generic type, you can see it as "var" in javascript
def variable

// Create a String variable
String s = "apan sover"

// Create a map variable
def m = [:]

m["apa"] = "sover"

// Create a String array
String[] sa = ["string"]

// loop through a string array
String[] sa = ["string"]
sa.each{element -> 
    println (element)
}

// Check if any element in an array fulfills a conditon
String[] sa = ["string"]
def apa = sa.any{ element -> element == "string"} // apa = true

// Create an array from splitting a string
String apa = "apan sover"
def arr = apa.split() // arr = ["apan", "sover"]