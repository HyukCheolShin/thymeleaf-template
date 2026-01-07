/**
 * API and Core Logic
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
     * Common API wrapper for $.ajax
     * @param {Object} options - $.ajax options
     */
    /**
     * Common API wrapper for $.ajax
     * @param {Object} options - $.ajax options
     */
    api(options) {
        const defaults = {
            type: 'GET',
            dataType: 'json',
            error: (xhr) => {
                App.handleAjaxError(xhr);
            }
        };

        // Intercept success to check response.code
        const originalSuccess = options.success;

        options.success = (response) => {
            if (response && response.code === 'SUCCESS') {
                if (originalSuccess) {
                    originalSuccess(response);
                }
            } else {
                // Business Logic Fail
                if (options.error) {
                    // Create a mock XHR-like object or just pass response message
                    // For consistency, let's trigger the error handler or alert directly
                    // But options.error usually expects xhr. 
                    // Let's just alert the message for now to be simple, or call App.handleAjaxError if we can mock it?
                    // Actually, if it's a 200 OK but code!=SUCCESS, it enters here.
                    const msg = response.message || '처리 중 오류가 발생했습니다.';
                    alert(msg);
                } else {
                    const msg = response.message || '처리 중 오류가 발생했습니다.';
                    alert(msg);
                }
            }
        };

        const settings = $.extend({}, defaults, options);
        return $.ajax(settings);
    },

    get(url, params, options) {
        return this.api($.extend({
            url: url,
            type: 'GET',
            data: params
        }, options));
    },

    post(url, data, options) {
        return this.api($.extend({
            url: url,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data)
        }, options));
    },

    put(url, data, options) {
        return this.api($.extend({
            url: url,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data)
        }, options));
    },

    delete(url, data, options) {
        return this.api($.extend({
            url: url,
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(data)
        }, options));
    }
};

// Initialize CSRF on document ready if jQuery is available
$(() => {
    App.initCsrf();
});
