package com.fastaccess.data.service;

import android.support.annotation.NonNull;

import com.fastaccess.data.dao.BranchesModel;
import com.fastaccess.data.dao.CommentRequestModel;
import com.fastaccess.data.dao.CreateMilestoneModel;
import com.fastaccess.data.dao.LabelModel;
import com.fastaccess.data.dao.MarkdownModel;
import com.fastaccess.data.dao.MilestoneModel;
import com.fastaccess.data.dao.Pageable;
import com.fastaccess.data.dao.RepoSubscriptionModel;
import com.fastaccess.data.dao.model.Comment;
import com.fastaccess.data.dao.model.Commit;
import com.fastaccess.data.dao.model.Release;
import com.fastaccess.data.dao.model.Repo;
import com.fastaccess.data.dao.model.RepoFile;
import com.fastaccess.data.dao.model.User;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Kosh on 10 Dec 2016, 3:16 PM
 */
public interface RepoService {


    @NonNull @GET @Headers("Accept: application/vnd.github.VERSION.raw")
    Observable<String> getFileAsStream(@Url String url);

    @NonNull @POST("markdown")
    Observable<String> convertReadmeToHtml(@Body MarkdownModel model);

    @NonNull @GET("repos/{login}/{repoId}") @Headers({"Accept: application/vnd.github.drax-preview+json"})
    Observable<Repo> getRepo(@Path("login") String login, @Path("repoId") String repoId);

    @NonNull @DELETE("repos/{login}/{repoId}")
    Observable<Response<Boolean>> deleteRepo(@Path("login") String login, @Path("repoId") String repoId);

    @NonNull @GET @Headers("Accept: application/vnd.github.html")
    Observable<String> getReadmeHtml(@NonNull @Url String url);

    @NonNull @GET("user/starred/{owner}/{repo}")
    Observable<Response<Boolean>> checkStarring(@NonNull @Path("owner") String login, @NonNull @Path("repo") String repoId);

    @NonNull @PUT("user/starred/{owner}/{repo}")
    Observable<Response<Boolean>> starRepo(@NonNull @Path("owner") String login, @NonNull @Path("repo") String repoId);

    @NonNull @DELETE("user/starred/{owner}/{repo}")
    Observable<Response<Boolean>> unstarRepo(@NonNull @Path("owner") String login, @NonNull @Path("repo") String repoId);

    @NonNull @POST("/repos/{owner}/{repo}/forks")
    Observable<Repo> forkRepo(@NonNull @Path("owner") String login, @NonNull @Path("repo") String repoId);

    @NonNull @GET("repos/{owner}/{repo}/subscription")
    Observable<RepoSubscriptionModel> isWatchingRepo(@Path("owner") String owner, @Path("repo") String repo);

    @NonNull @PUT("user/subscriptions/{owner}/{repo}")
    Observable<Response<Boolean>> watchRepo(@Path("owner") String owner, @Path("repo") String repo);

    @NonNull @DELETE("user/subscriptions/{owner}/{repo}")
    Observable<Response<Boolean>> unwatchRepo(@Path("owner") String owner, @Path("repo") String repo);

    @NonNull @GET("repos/{owner}/{repo}/commits")
    Observable<Pageable<Commit>> getCommits(@Path("owner") String owner, @Path("repo") String repo,
                                            @NonNull @Query("sha") String branch, @Query("page") int page);

    @NonNull @GET("repos/{owner}/{repo}/releases")
    @Headers("Accept: application/vnd.github.VERSION.full+json")
    Observable<Pageable<Release>> getReleases(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);

    @NonNull @GET("repos/{owner}/{repo}/tags")
    @Headers("Accept: application/vnd.github.VERSION.full+json")
    Observable<Pageable<Release>> getTagReleases(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);

    @NonNull @GET("repos/{owner}/{repo}/contributors")
    Observable<Pageable<User>> getContributors(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);

    @NonNull @GET("repos/{owner}/{repo}/commits/{sha}")
    @Headers("Accept: application/vnd.github.VERSION.full+json, application/vnd.github.squirrel-girl-preview")
    Observable<Commit> getCommit(@Path("owner") String owner, @Path("repo") String repo, @Path("sha") String sha);

    @NonNull @GET("repos/{owner}/{repo}/commits/{sha}/comments")
    @Headers("Accept: application/vnd.github.VERSION.full+json, application/vnd.github.squirrel-girl-preview")
    Observable<Pageable<Comment>> getCommitComments(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo,
                                                    @NonNull @Path("sha") String ref, @Query("page") int page);

    @NonNull @POST("repos/{owner}/{repo}/commits/{sha}/comments")
    @Headers("Accept: application/vnd.github.VERSION.full+json, application/vnd.github.squirrel-girl-preview")
    Observable<Comment> postCommitComment(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo,
                                          @NonNull @Path("sha") String ref, @Body CommentRequestModel model);

    @NonNull @PATCH("repos/{owner}/{repo}/comments/{id}")
    @Headers("Accept: application/vnd.github.VERSION.full+json, application/vnd.github.squirrel-girl-preview")
    Observable<Comment> editCommitComment(@Path("owner") String owner, @Path("repo") String repo, @Path("id") long id,
                                          @Body CommentRequestModel body);

    @NonNull @DELETE("repos/{owner}/{repo}/comments/{id}")
    Observable<Response<Boolean>> deleteComment(@Path("owner") String owner, @Path("repo") String repo, @Path("id") long id);

    @NonNull @GET("repos/{owner}/{repo}/contents/{path}")
    Observable<Pageable<RepoFile>> getRepoFiles(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo,
                                                @NonNull @Path("path") String path, @NonNull @Query("ref") String ref);

    @NonNull @GET("repos/{owner}/{repo}/labels")
    Observable<Pageable<LabelModel>> getLabels(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo);

    @NonNull @POST("repos/{owner}/{repo}/labels")
    Observable<LabelModel> addLabel(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo, @Body LabelModel body);

    @NonNull @GET("repos/{owner}/{repo}/collaborators/{username}")
    Observable<Response<Boolean>> isCollaborator(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo,
                                                 @NonNull @Path("username") String username);

    @NonNull @GET("repos/{owner}/{repo}/branches")
    Observable<Pageable<BranchesModel>> getBranches(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo);

    @NonNull @GET("repos/{owner}/{repo}/milestones")
    Observable<Pageable<MilestoneModel>> getMilestones(@Path("owner") String owner, @Path("repo") String repo);

    @NonNull @POST("repos/{owner}/{repo}/milestones")
    Observable<MilestoneModel> createMilestone(@Path("owner") String owner, @Path("repo") String repo,
                                               @Body CreateMilestoneModel create);

    @NonNull @GET("repos/{owner}/{repo}/assignees")
    Observable<Pageable<User>> getAssignees(@Path("owner") String owner, @Path("repo") String repo);

    @NonNull @GET("repos/{owner}/{repo}/commits?per_page=1")
    Observable<Pageable<Commit>> getCommitCounts(@Path("owner") String owner, @Path("repo") String repo);
}
