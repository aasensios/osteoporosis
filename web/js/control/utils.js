/**
 * @name utils.js
 * @description This module contains some versatile functions that can be reused
 * in many JavaScript scripts.
 * @date 2019-01-02
 * @author Alejandro Asensio
 * @version 1.0
 */

// -----------------------------------------------------------------------------

/**
 * @name applyStyles
 * @description Applies some bootstrap styles to the document.
 * @author Alejandro Asensio
 * @version 1.1 ChangeLog: #error id styled in red text.
 * @date 2018-12-28
 * @param none
 * @return none
 */
function applyStyles() {

  $("fieldset").addClass("form-group");
//  $("label").addClass("input-group-text");
//  $("input").addClass("form-control");
//  $(".inputclass").addClass("input-group mb-3");
//  $("select").addClass("custom-select");
  $("table").addClass("table table-striped");
  $("thead").addClass("thead-dark");
//  $("button").addClass("btn btn-primary"); // button default color: blue
//  $("[value=cancel], [value=close]").addClass("btn btn-danger"); // red
//  $("[id=print]").addClass("btn btn-success"); // green
  $("#error").removeClass("text-dark").addClass("text-danger"); // red text
                
}

// -----------------------------------------------------------------------------
