package com.example1.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class BlogController {

    private final
    BlogRepository blogRepository;

    @Autowired
    public BlogController(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @RequestMapping(path = "/",method = RequestMethod.GET)
    public String index(Model model) {
        List<Blog> blogs = blogRepository.findAll();
        model.addAttribute("blogs", blogs);
        return "index";
    }

    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    public Blog show(@PathVariable String id) {
        int blogId = Integer.parseInt(id);
        return blogRepository.findById(blogId).get();
    }

    @RequestMapping(path = "/addblog",method = RequestMethod.GET)
    public String showSignUpForm(Blog blog) {
        return "add-blog";
    }

    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public String create(Blog blog, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-blog";
        }

        blogRepository.save(blog);
        return "redirect:/";
    }


    @RequestMapping(path = "/edit/{id}",method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        Blog blog = blogRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));

        model.addAttribute("blog", blog);
        return "update-blog";
    }

    @RequestMapping(path = "/update/{id}",method = RequestMethod.POST)
    public String update(@PathVariable("id") String id, Blog blog,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            blog.setId(Integer.parseInt(id));
            return "update-blog";
        }

        blogRepository.save(blog);
        return "redirect:/";

        /*int blogId = Integer.parseInt(id);
        // getting blog
        Blog blog = blogRepository.findById(blogId).get();
        blog.setTitle(body.get("title"));
        blog.setContent(body.get("content"));
        return blogRepository.save(blog);*/
    }


    @RequestMapping(path = "/delete/{id}",method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") String id, Model model) {
        Blog blog = blogRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));
        blogRepository.delete(blog);
        return "redirect:/";
    }

}