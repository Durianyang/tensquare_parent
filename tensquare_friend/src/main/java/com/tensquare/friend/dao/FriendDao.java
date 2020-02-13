package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Author: Durian
 * Date: 2020/2/13 20:46
 * Description:
 */
public interface FriendDao extends JpaRepository<Friend, String>
{
    /**
     * 根据用户ID与被关注用户ID查询记录个数
     *
     * @param userid   用户Id
     * @param friendid 被关注用户Id
     * @return 记录数
     */
    @Query("select count(f) from Friend f where f.userid = ?1 and f.friendid = ?2")
    int selectCount(String userid, String friendid);

    /**
     * 更新为互相喜欢
     *
     * @param userid   用户id
     * @param friendId 被关注用户ID
     * @param islike   是否相互喜欢
     */
    @Modifying // 更新操作必不可少
    @Query("update Friend f set f.islike = ?3 where f.userid = ?1 and f.friendid = ?2")
    void updateLike(String userid, String friendId, String islike);

    /**
     * 删除好友
     * @param userid 用户id
     * @param friendid 被操作用户id
     */
    @Modifying
    @Query("delete from Friend f where f.userid = ?1 and f.friendid = ?2")
    void deleteFriend(String userid, String friendid);



}
