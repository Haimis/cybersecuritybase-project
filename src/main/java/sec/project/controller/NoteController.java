package sec.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import sec.project.domain.Note;
import sec.project.domain.User;
import sec.project.repository.NoteRepository;

@Controller
@SessionAttributes("user")
public class NoteController {
    
    @Autowired
    private NoteRepository noteRepository;

    @RequestMapping("/notes")
public String showNotes(Model model) {
    if (!model.containsAttribute("user")) {
        return "redirect:/signin";
    }
        
    Map mod = model.asMap();
    User u = (User) mod.get("user");
    List<Note> notes = noteRepository.findAll();
    List<String> content = new ArrayList<>();
    notes.forEach((n) -> {
        if (n.getUser().equals(u)) {
            content.add(n.getContent());
        }
    });

    model.addAttribute("notes", content);
    model.addAttribute("name", u.getName());
    return "notes";

    }
    
    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public String addNote(Model model, @RequestParam(required = true) String note) {
        Map mod = model.asMap();
        User u = (User) mod.get("user");
        Note n = new Note(note, u);
        noteRepository.save(n);
        return "redirect:/notes";   
    }
    
    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        //sessionStatus.setComplete();
        return "logout";
    }
}
