# Clone the git repository. https://git-scm.com/docs/git-clone
git clone https://github.com/dev4242/project-x.git

# Returns a list of changed files from the current main branch. https://git-scm.com/docs/git-diff
git diff --name-only origin/main

# Prints a value
echo "value"

# Variable assignment
variable="apa"

# Print variable
echo "${variable}"

# Source the setup script mentioned in the path. More info: https://linuxize.com/post/bash-source-command/
source "./ci/setup.sh"

# Run a script. By giving a path to a script, without anything before it, you run a script.
./apps/app00/test/test.sh


