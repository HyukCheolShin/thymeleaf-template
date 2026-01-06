/**
 * UI Helper Functions
 * Depends on api.js (for App object) and jQuery
 */

if (typeof App === 'undefined') {
    console.error('App is not defined. Make sure api.js is loaded first.');
} else {
    /**
     * Render pagination controls
     * @param {Object} data - PageResponse object
     * @param {String} jsFunction - JS function to call on click (default: 'loadUsers')
     */
    App.renderPagination = function (data, jsFunction = 'loadUsers') {
        const $ul = $('nav ul.pagination');
        const $container = $ul.closest('.pagination-container');

        $ul.empty();

        if (data.totalPages <= 1) {
            $container.hide();
            return;
        }

        $container.show();

        // Previous
        const prevDisabled = data.currentPage === 1 ? 'disabled' : '';
        $ul.append(`
            <li class="page-item ${prevDisabled}">
                <a class="page-link" href="#" onclick="${jsFunction}(${data.currentPage - 1}); return false;">&laquo;</a>
            </li>
        `);

        // Page Numbers
        const startPage = Math.max(1, data.currentPage - 2);
        const endPage = Math.min(data.totalPages, data.currentPage + 2);

        for (let i = startPage; i <= endPage; i++) {
            const active = i === data.currentPage ? 'active' : '';
            $ul.append(`
                <li class="page-item ${active}">
                    <a class="page-link" href="#" onclick="${jsFunction}(${i}); return false;">${i}</a>
                </li>
            `);
        }

        // Next
        const nextDisabled = data.currentPage === data.totalPages ? 'disabled' : '';
        $ul.append(`
            <li class="page-item ${nextDisabled}">
                <a class="page-link" href="#" onclick="${jsFunction}(${data.currentPage + 1}); return false;">&raquo;</a>
            </li>
        `);
    };
}
