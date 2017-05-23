module.exports = function (grunt) {
  // Project Configuration
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    eslint: {
      options: {
        configFile: '.eslintrc.json',
        fix: true,
      },
      target: ['*.js', 'public/app/**/*.js'],
    },
    prettify: {
      all: {
        expand: true,
        src: ['public/**/*.html'],
      },
    },
  });

  // Load NPM tasks
  grunt.loadNpmTasks('grunt-eslint');
  grunt.loadNpmTasks('grunt-prettify');

  // Making grunt default to force in order not to break the project.
  grunt.option('force', true);

  // Default task(s).
  grunt.registerTask('default', ['eslint', 'prettify']);
};
