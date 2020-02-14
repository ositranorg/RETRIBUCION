package com.kemal.spring.web.controllers.viewControllers;



import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kemal.spring.web.dto.UserDto;

/**
 * Created by Keno&Kemo on 17.11.2017..
 */
@Controller
@RequestMapping("")
@Scope("session")
public class RegisterController {
    /*private UserService userService;
    public RegisterController(UserService userService, EmailService emailService) {
        this.userService = userService;
    }*/

    @PostMapping(value = "/submit-registration")
    public ModelAndView saveUser(ModelAndView modelAndView, @ModelAttribute("userDto") @Valid final UserDto userDto,
                                 BindingResult bindingResult, HttpServletRequest request, Errors errors){

       /* User emailExists = userService.findByEmail(userDto.getEmail());
        User userNameExists = userService.findByUsername(userDto.getUsername());

        System.out.println(emailExists);

        if (emailExists != null) {
            modelAndView.setViewName("website/register");
            bindingResult.rejectValue("email", "emailAlreadyExists");
        }

        if (userNameExists!= null) {
            modelAndView.setViewName("website/register");
            bindingResult.rejectValue("username", "usernameAlreadyExists");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("website/register");
        }
        else { // new user so we create user and send confirmation e-mail

            User user = userService.createNewAccount(userDto);
            // Disable user until they click on confirmation link in email

            user.setEnabled(true);
            userService.save(user);


           

            modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to "
                                    + userDto.getEmail());
            modelAndView.setViewName("website/registered");
        }
*/
        return modelAndView;
    }
}
