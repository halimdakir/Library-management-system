package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.entity.Member;
import se.iths.library.repository.MemberRepository;

import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member){
        return memberRepository.save(member);
    }
    public void deleteMemberById(Long id){
        Optional<Member> foundMember = memberRepository.findById(id);
        memberRepository.deleteById(foundMember.get().getId());
    }
    public Optional<Member> getMemberById(Long id){
        return memberRepository.findById(id);
    }
    public Iterable<Member> getAllMembers(){
        return memberRepository.findAll();
    }
}
