package com.example1.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BlogController {

    private final
    BlogRepository blogRepository;

    @Autowired
    public BlogController(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @GetMapping("/blog")
    public List<Blog> index(){
        return blogRepository.findAll();
    }


    @GetMapping("/index")
    public String index(Model model) {
        List<Blog> blogs = blogRepository.findAll();
        model.addAttribute("blogs", blogs);
        return "index";
    }

    @GetMapping("/blog/{id}")
    public Blog show(@PathVariable String id){
        int blogId = Integer.parseInt(id);
        return blogRepository.findById(blogId).get();
    }

    @PostMapping("/blog/search")
    public List<Blog> search(@RequestBody Map<String, String> body){
        String searchTerm = body.get("text");
        return blogRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
    }

    @GetMapping("/addblog")
    public String showSignUpForm(Blog blog) {
        return "add-blog";
    }

    @PostMapping("/add")
    public String create(Blog blog, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "add-blog";
        }

        blogRepository.save(blog);
        return "redirect:/index";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        Blog blog = blogRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));

        model.addAttribute("blog", blog);
        return "update-blog";
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") String id, Blog blog,
                       BindingResult result, Model model){

        if (result.hasErrors()) {
            blog.setId(Integer.parseInt(id));
            return "update-blog";
        }

        blogRepository.save(blog);
        return "redirect:/index";

        /*int blogId = Integer.parseInt(id);
        // getting blog
        Blog blog = blogRepository.findById(blogId).get();
        blog.setTitle(body.get("title"));
        blog.setContent(body.get("content"));
        return blogRepository.save(blog);*/
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id, Model model) {
        Blog blog = blogRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));
        blogRepository.delete(blog);
        return "redirect:/index";
    }

}