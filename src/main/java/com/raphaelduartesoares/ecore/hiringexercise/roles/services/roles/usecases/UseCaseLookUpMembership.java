package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.usecases;

import java.util.List;

import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.RequestLookUpMembershipDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos.ResponseMembershipDto;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain.Membership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces.IRepositoryMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.InvalidInputDataException;

public class UseCaseLookUpMembership {

    private IRepositoryMembership repositoryMembership;

    public UseCaseLookUpMembership(IRepositoryMembership repositoryMembership) {
        this.repositoryMembership = repositoryMembership;
    }

    public List<ResponseMembershipDto> lookUpMembership(RequestLookUpMembershipDto requestLookUpMembershipDto)
            throws InvalidInputDataException {
        boolean isRoleCodeNullOrEmpty = requestLookUpMembershipDto.roleCode == null
                || requestLookUpMembershipDto.roleCode.isBlank();
        boolean isUserIdNullOrEmpty = requestLookUpMembershipDto.userId == null
                || requestLookUpMembershipDto.userId.isBlank();
        boolean isTeamIdNullOrEmpty = requestLookUpMembershipDto.teamId == null
                || requestLookUpMembershipDto.teamId.isBlank();
        if (isRoleCodeNullOrEmpty && isUserIdNullOrEmpty && isTeamIdNullOrEmpty) {
            throw new InvalidInputDataException("Invalid input data", "Request must have at least one parameter");
        }

        List<Membership> memberships = Membership
                .fromEntities(
                        repositoryMembership.findAllByRoleAndOrTeamIdAndOrUserId(requestLookUpMembershipDto.roleCode,
                                requestLookUpMembershipDto.teamId, requestLookUpMembershipDto.userId));

        return Membership.toDtoList(memberships);
    }

}
