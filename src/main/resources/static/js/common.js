/**
 * Common JavaScript for Application
 */

const App = {
    /**
     * Initialize CSRF Token for AJAX requests
     */
    initCsrf() {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");

        if (token && header) {
            $.ajaxSetup({
                beforeSend: (xhr) => {
                    xhr.setRequestHeader(header, token);
                }
            });
        }
    },

    /**
     * Handle AJAX errors common logic
     * @param {Object} xhr - The XHR object
     * @param {String} defaultMsg - Default error message if no detail is found
     * @param {Boolean} goBack - Whether to go back to previous page on error
     */
    handleAjaxError(xhr, defaultMsg, goBack) {
        let msg = defaultMsg || '오류가 발생했습니다.';
        if (xhr.responseJSON && xhr.responseJSON.message) {
            msg = xhr.responseJSON.message;
        }
        alert(msg);

        if (goBack) {
            window.history.back();
        }
    }
};

// Initialize CSRF on document ready if jQuery is available
$(() => {
    App.initCsrf();
});
