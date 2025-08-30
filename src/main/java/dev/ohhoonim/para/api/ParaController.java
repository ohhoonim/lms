package dev.ohhoonim.para.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.container.Response;
import dev.ohhoonim.component.container.ResponseCode;
import dev.ohhoonim.component.container.Search;
import dev.ohhoonim.para.Note;
import dev.ohhoonim.para.Para;
import dev.ohhoonim.para.Para.Project;
import dev.ohhoonim.para.Para.Shelf;
import dev.ohhoonim.para.activity.service.ParaService;

@RestController
public class ParaController {

    private final ParaService paraService;

    public ParaController(ParaService paraService) {
        this.paraService = paraService;
    }

    @GetMapping("/para/{paraId}/notes")
    public List<Note> notes(
            @PathVariable("paraId") Id paraId,
            @RequestParam("category") String category) {
        return paraService.notes(Para.getParaInstance(paraId, category));
    }

    @PostMapping("/para/{paraId}/registNote")
    public List<Note> registNote(
            @PathVariable("paraId") Id paraId,
            @RequestBody ParaNoteReq paraReq) {
        return paraService.registNote(
                Para.getParaInstance(paraId, paraReq.getCategory()),
                Id.valueOf(paraReq.getNoteId()));
    }


    @PostMapping("/para/{paraId}/removeNote")
    public List<Note> removeNote(
            @PathVariable("paraId") String paraId,
            @RequestBody ParaNoteReq paraReq) {
        return paraService.removeNote(
                Para.getParaInstance(
                        Id.valueOf(paraId),
                        paraReq.getCategory()),
                Id.valueOf(paraReq.getNoteId()));
    }

    @PostMapping("/para/{paraId}/moveTo")
    public Para moveToPara(
            @PathVariable("paraId") String paraId,
            @RequestBody ParaNoteReq paraReq) {
        return paraService.moveToPara(
                Para.getParaInstance(Id.valueOf(paraId), paraReq.getCategory()),
                Shelf.getParaType(paraReq.getTargetCategory()))
                .orElseThrow(() -> new RuntimeException("Para이동에 실패하였습니다."));
    }

    @GetMapping("/para/{paraId}")
    public Para getPara(
            @PathVariable("paraId") Id paraId,
            @RequestParam("category") String category) {
        return paraService.getPara(Para.getParaInstance(paraId, category))
                .orElseThrow(() -> new RuntimeException("category를 찾을 수 없습니다."));
    }

    @PostMapping("/para/add")
    public Para addPara(@RequestBody ParaReq newPara) {
        return paraService.addPara(Para.getNewParaInstance(
                newPara.getCategory(),
                newPara.getTitle(),
                newPara.getContent()))
                .orElseThrow(() -> new RuntimeException("category를 추가하지 못했습니다."));
    }

    @PostMapping("/para/{paraId}/modify")
    public Para modifyPara(
            @PathVariable("paraId") Id paraId,
            @RequestBody ParaNoteReq paraReq) {
        return paraService.modifyPara(
                Para.getParaInstance(paraId, paraReq.getCategory()))
                .orElseThrow(() -> new RuntimeException("Para 수정에 실패하였습니다."));
    }

    @PostMapping("/para/{paraId}/remove")
    public Response removePara(
            @PathVariable("paraId") Id paraId,
            @RequestBody ParaNoteReq paraReq) {
        paraService.removePara(Para.getParaInstance(paraId, paraReq.getCategory()));
        return new Response.Success(
                ResponseCode.SUCCESS,
                null);
    }

    @PostMapping("/para/searchShelves")
    public List<Shelf> findShelves(@RequestBody Search<ParaReq> search) {
        return paraService.findShelves(search.getReq().getTitle(), search.getPage());
    }

    @PostMapping("/para/searchProjects")
    public List<Project> findProjects(@RequestBody Search<ParaReq> search) {
        return paraService.findProjects(search.getReq().getTitle(), search.getPage());
    }

}
