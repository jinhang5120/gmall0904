package com.jh.gmall.service;

import com.jh.gmall.entity.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface UmsMemberService extends IService<UmsMember> {
    public UmsMember add(int i);

    UmsMember login(UmsMember umsMember);

    void addUserToken(String token, Long id);

    UmsMember selectById(Long memberId);
}
