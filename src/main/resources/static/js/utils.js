/**
 * Utility Functions
 * Depends on api.js (for App object)
 */

if (typeof App === 'undefined') {
    console.error('App is not defined. Make sure api.js is loaded first.');
} else {
    /**
     * Format date string to YYYY-MM-DD HH:mm:ss
     * @param {String} dateString 
     * @returns {String} formatted date or empty string
     */
    App.formatDate = function (dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        const pad = (num) => String(num).padStart(2, '0');
        return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
    };
}
