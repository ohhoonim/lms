package dev.ohhoonim.component.auditing.model;

public sealed interface DataBy permits Created, Modified, Entity, Id, MasterCode {
    
}
