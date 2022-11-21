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
        checkIfRequestHasAtLeastOneField(requestLookUpMembershipDto);

        List<Membership> memberships = Membership
                .fromEntities(
                        repositoryMembership.findAllByRoleAndOrTeamIdAndOrUserId(requestLookUpMembershipDto.role,
                                requestLookUpMembershipDto.team, requestLookUpMembershipDto.user));

        return Membership.toDtoList(memberships);
    }

    private void checkIfRequestHasAtLeastOneField(RequestLookUpMembershipDto requestLookUpMembershipDto)
            throws InvalidInputDataException {
        boolean isRoleCodeNullOrEmpty = requestLookUpMembershipDto.role == null
                || requestLookUpMembershipDto.role.isBlank();
        boolean isUserIdNullOrEmpty = requestLookUpMembershipDto.user == null
                || requestLookUpMembershipDto.user.isBlank();
        boolean isTeamIdNullOrEmpty = requestLookUpMembershipDto.team == null
                || requestLookUpMembershipDto.team.isBlank();
        if (isRoleCodeNullOrEmpty && isUserIdNullOrEmpty && isTeamIdNullOrEmpty) {
            throw new InvalidInputDataException("Invalid input data", "Request must have at least one parameter");
        }
    }

}
