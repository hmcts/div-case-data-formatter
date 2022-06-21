#!groovy
import uk.gov.hmcts.contino.GithubAPI

class LabelStates {
    private ArrayList Labels = []
    private LinkedHashMap LabelStates = [:]
    private Boolean AtLeastOneEnabled = false
    private String Pattern = ""
    private String EnableAll = ""

    private void setLabelStates() {
        def states = [:]
        // Get labels from GitHub repo
        def repoLabels = new GithubAPI(this).getLabelsbyPattern(env.BRANCH_NAME, this.Pattern)

        // Check existence of provided label, and store result in states map
        def getState = { label ->
            if (label == "atLeastOneEnabled") {
                return
            }
            def labelExists = repoLabels.contains(label) ? true : false
            states.put(label, labelExists)
        }

        // Iterate over labels List to build LabelStates map
        this.Labels.each { label -> getState(label) }

        // Set state of EnableAll label )If a label matching this string exists, all label tests will return True)
        if (!states.containsKey(this.EnableAll)) {
            getState(this.EnableAll)
        }

        this.LabelStates = states
    }

    Boolean isAllEnabled() {
        return (this.LabelStates[this.EnableAll])
    }
    
    Boolean getLabelState(label) {
        return (this.isAllEnabled() || this.LabelStates.get(label))
    }

    Boolean isAtLeastOneEnabled() {
        return this.AtLeastOneEnabled
    }

    LabelStates(final ArrayList labels, final String pattern = "enable_", final String enableAllLabel = "enable_all_tests_and_scans") {
        // Define labels
        this.Labels = labels
        // Define pattern
        this.Pattern = pattern
        // Define EnableAll label name (If a label matching this string exists, all label tests will return True)
        this.EnableAll = enableAllLabel
        // Define label states
        this.setLabelStates()
        // Check if at least one label state is true
        this.AtLeastOneEnabled = this.LabelStates.any {key, value -> value == true }
    }
}

return this
