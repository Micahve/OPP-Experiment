package system

import rego.v1

default main := {
    "decision": false,
    "context": {
        "id": "deny"
    }
}

main := {
    "decision": true,
    "context": {
        "id": "allow"
    }
} if {
    true
}