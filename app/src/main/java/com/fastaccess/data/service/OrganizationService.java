package com.fastaccess.data.service;

import android.support.annotation.NonNull;

import com.fastaccess.data.dao.Pageable;
import com.fastaccess.data.dao.TeamsModel;
import com.fastaccess.data.dao.model.Repo;
import com.fastaccess.data.dao.model.User;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Kosh on 22 Mar 2017, 6:44 PM
 */
public interface OrganizationService {

    @GET("orgs/{org}/members/{username}")
    Observable<Response<Boolean>> isMember(@NonNull @Path("org") String org, @NonNull @Path("username") String username);

    @GET("orgs/{org}") Observable<User> getOrganization(@NonNull @Path("org") String org);

    @GET("user/orgs") Observable<Pageable<User>> getMyOrganizations();

    @GET("users/{user}/orgs") Observable<Pageable<User>> getMyOrganizations(@NonNull @Path("user") String user);

    @GET("orgs/{org}/teams") Observable<Pageable<TeamsModel>> getOrgTeams(@NonNull @Path("org") String org, @Query("page") int page);

    @GET("orgs/{org}/members") Observable<Pageable<User>> getOrgMembers(@NonNull @Path("org") String org, @Query("page") int page);

    @GET("teams/{id}/members") Observable<Pageable<User>> getTeamMembers(@Path("id") long id, @Query("page") int page);

    @GET("teams/{id}/repos") Observable<Pageable<Repo>> getTeamRepos(@Path("id") long id, @Query("page") int page);
}
