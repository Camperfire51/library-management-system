package com.camperfire.library_management_system.service;

import com.camperfire.library_management_system.entity.Member;
import com.camperfire.library_management_system.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // Create or Update Member
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    // Read All Members
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // Read Member by ID
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    // Delete Member by ID
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
