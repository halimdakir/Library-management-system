package se.iths.library.controller;

import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.Member;
import se.iths.library.service.MemberService;

import java.util.GregorianCalendar;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/id/{id}")
    public Optional<Member> getOneMemberById(@PathVariable Long id){
        return memberService.getMemberById(id);
    }
    @GetMapping("/all")
    public Iterable<Member> getAllMembers(){
        return memberService.getAllMembers();
    }
    @PostMapping("/new")
    public Member createNewMember(@RequestBody Member member) {
        return memberService.createMember(member);
    }
    @DeleteMapping("/id/{id}")
    public void deleteMemberById(@PathVariable Long id){
        memberService.deleteMemberById(id);
    }
}
