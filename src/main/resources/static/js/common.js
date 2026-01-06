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
    },

    /**
     * Render pagination controls
     * @param {Object} data - PageResponse object
     * @param {String} keyword - Search keyword
     * @param {String} searchType - Search type
     * @param {String} jsFunction - JS function to call on click (default: 'loadUsers')
     */
    renderPagination(data, keyword, searchType, jsFunction = 'loadUsers') {
        const container = $('nav ul.pagination');
        container.empty();

        if (data.totalPages <= 1) return;

        // Previous
        const prevDisabled = data.currentPage === 1 ? 'disabled' : '';
        container.append(`
            <li class="page-item ${prevDisabled}">
                <a class="page-link" href="#" onclick="${jsFunction}(${data.currentPage - 1}); return false;">&laquo;</a>
            </li>
        `);

        // Page Numbers
        const startPage = Math.max(1, data.currentPage - 2);
        const endPage = Math.min(data.totalPages, data.currentPage + 2);

        for (let i = startPage; i <= endPage; i++) {
            const active = i === data.currentPage ? 'active' : '';
            container.append(`
                <li class="page-item ${active}">
                    <a class="page-link" href="#" onclick="${jsFunction}(${i}); return false;">${i}</a>
                </li>
            `);
        }

        // Next
        const nextDisabled = data.currentPage === data.totalPages ? 'disabled' : '';
        container.append(`
            <li class="page-item ${nextDisabled}">
                <a class="page-link" href="#" onclick="${jsFunction}(${data.currentPage + 1}); return false;">&raquo;</a>
            </li>
        `);
    }
};

// Initialize CSRF on document ready if jQuery is available
$(() => {
    App.initCsrf();
});
