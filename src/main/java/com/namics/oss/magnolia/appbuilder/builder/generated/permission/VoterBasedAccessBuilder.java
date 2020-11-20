package com.namics.oss.magnolia.appbuilder.builder.generated.permission;

import info.magnolia.cms.security.operations.VoterBasedConfiguredAccessDefinition;
import info.magnolia.voting.Voter;
import info.magnolia.voting.Voting;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:02.523694"
)
public class VoterBasedAccessBuilder extends VoterBasedConfiguredAccessDefinition {
	public VoterBasedAccessBuilder voters(List<Voter> voters) {
		this.setVoters(voters);
		return this;
	}

	public VoterBasedAccessBuilder voter(Voter voter) {
		this.addVoter(voter);
		return this;
	}

	public VoterBasedAccessBuilder voting(Voting voting) {
		this.setVoting(voting);
		return this;
	}

	public VoterBasedAccessBuilder superuserRole(String superuserRole) {
		this.setSuperuserRole(superuserRole);
		return this;
	}

	public VoterBasedAccessBuilder voters(Voter... voters) {
		this.setVoters(Arrays.asList(voters));
		return this;
	}
}
