package roleauth

import rego.v1

default allow := false

allow if {
    input.role == "Admin"
}

allow if {
    input.role == "Researcher"
    input.method == "GET"
    input.path == "/users"
}