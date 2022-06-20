#!groovy
import uk.gov.hmcts.contino.GithubAPI

class AdditionalTestLabels {
    private Labels = []
    private LabelStates = [:]
    private AtLeastOneEnabled = false

    private void setLabelStates() {
        def states = [:]
        // Get labels from GitHub repo
        def repoLabels = new GithubAPI(this).getLabelsbyPattern(env.BRANCH_NAME, "enable_")

        // Check existence of provided label, and store in states map
        def getState = { label ->
            if (label == "atLeastOneEnabled") {
                return
            }
            def labelExists = repoLabels.contains(label) ? true : false
            states.put(label, labelExists)
        }

        // Iterate over labels List to build LabelStates map
        this.Labels.each { label -> getState(label) }

        // Hardcode enable_all_tests_and_scans label
        if (!states.containsKey("enable_all_tests_and_scans")) {
            getState("enable_all_tests_and_scans")
        }

        this.LabelStates = states
    }

    Boolean getLabelState(label) {
        return (this.LabelStates.enable_all_tests_and_scans || this.LabelStates.get(label))
    }

    Boolean isAtLeastOneEnabled() {
        return this.AtLeastOneEnabled
    }

    AdditionalTestLabels(final ArrayList labels) {
        // Define labels
        this.Labels = labels
        // Define label states
        this.setLabelStates()
        // Check if at least one label state is true
        this.AtLeastOneEnabled = this.LabelStates.any {key, value -> value == true }
    }
}
