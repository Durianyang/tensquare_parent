package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Author: Durian
 * Date: 2020/2/13 21:18
 * Description:
 */
public interface NoFriendDao extends JpaRepository<NoFriend, String>
{
    /**
     * 根据用户ID与不被喜欢用户ID查询记录个数
     *
     * @param userid   用户Id
     * @param friendid 被关注用户Id
     * @return 记录数
     */
    @Query("select count(nf) from NoFriend  nf where nf.userid = ?1 and nf.friendid = ?2")
    int selectCount(String userid, String friendid);

    @Modifying
    @Query("delete from NoFriend nf where nf.userid = ?1 and nf.friendid = ?2")
    void deleteNoFriend(String userid, String friendid);
}
