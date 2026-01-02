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
    },

    /**
     * Format date string to YYYY-MM-DD HH:mm:ss
     * @param {String} dateString 
     * @returns {String} formatted date or empty string
     */
    formatDate(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        const pad = (num) => String(num).padStart(2, '0');
        return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
    }
};

// Initialize CSRF on document ready if jQuery is available
$(() => {
    App.initCsrf();
});
