package dev.ohhoonim.component.container;

import dev.ohhoonim.component.auditing.model.Id;

public record Page(
		Integer totalCount,
		Integer limit,
		Id lastSeenKey) {

	public Page {
		if (limit == null || limit.equals(0)) {
			limit = 10;
		}
	}

	public Page() {
		this(null, null, null);
	}

	public Page(Id lastSeenKey) {
		this(null, 20, lastSeenKey);
	}

}