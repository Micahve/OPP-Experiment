package router

import future.keywords.if

# Returns the correct policy path based on dataType + requestType
policy_path := path if {
    path := routes[input.dataType][input.requestType]
}

# Routing table
routes := {
    "SurveyData": {
        "full":       "survey/full",
        "aggregated": "survey/aggregated"
    },
    "WearablesData": {
        "full":       "wearables/full",
        "aggregated": "wearables/aggregated"
    }
}

# Default — catches unknown dataType or requestType combinations
default policy_path := "unknown"