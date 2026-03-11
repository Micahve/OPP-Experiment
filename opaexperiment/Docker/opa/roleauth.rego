package roleauth

import rego.v1

default allow := false
default redacted_fields := []

# Admin can access everything
allow if {
    input.subject.role == "Admin"
}

# Researcher can access research data
allow if {
    input.subject.role == "Researcher"
    input.action.name == "GET"
    input.resource.id == "/researchdata"
}

# Researchers get PII redacted
redacted_fields := ["subjectFirstName", "subjectLastName"] if {
    input.subject.role == "Researcher"
    input.resource.id == "/researchdata"
}