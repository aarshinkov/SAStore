const path = require('path');

module.exports = {
  entry: './src/main/webapp/WEB-INF/resources/admin/js/aos.js',
  output: {
    filename: './src/main/webapp/WEB-INF/resources/admin/js/aos.js',
    path: path.resolve(__dirname, 'dist'),
  },
};