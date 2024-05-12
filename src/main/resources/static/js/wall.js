const newPostFormBtnSpan = document.querySelector(".toggle-new-post-btn > span")
const newPostForm = document.querySelector(".new-post-form-container")

const toggleNewPostForm = () => {
    newPostForm.classList.toggle("hidden")
    newPostFormBtnSpan.innerText = newPostForm.classList.contains("hidden") ? "+" : "-"
}

const enableEdit = (event, id) => {
    const card = document.querySelector('#post-card-' + id);
    const contents = card.querySelector(".contents")
    const formContainer = card.querySelector(".edit-form-container")

    contents.classList.toggle("hidden")
    formContainer.classList.toggle("hidden")

    const editBtn = event.target;
    if (editBtn.classList.contains("editing")) {
        editBtn.classList.remove("editing");
        editBtn.setAttribute("value", "Edit")
    } else {
        editBtn.classList.add("editing");
        editBtn.setAttribute("value", "Cancel")
    }

    const contentTextarea = card.querySelector(".input-content")

    adjustTextareaHeight(contentTextarea)
    contentTextarea.addEventListener("input", function() {
        adjustTextareaHeight(this);
    })
}

const adjustTextareaHeight = (textarea) => {
    textarea.style.height = "";
    textarea.style.height = Math.min(textarea.scrollHeight, textarea.clientHeight) + "px";
}
