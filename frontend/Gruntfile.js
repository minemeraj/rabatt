module.exports = function (grunt) {
  // Project Configuration
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    eslint: {
      options: {
        configFile: '.eslintrc.json',
        fix: true,
      },
      target: ['*.js', 'client/controllers/**/*.js', 'client/services/**/*.js'],
    },
    prettify: {
      all: {
        expand: true,
        src: ['client/**/*.html'],
      },
    },
    concurrent: {
      tasks: ['nodemon', 'watch'],
      options: {
        logConcurrentOutput: true,
      },
    },
    nodemon: {
      dev: {
        script: 'index.js',
        options: {
          ignore: ['README.md', 'node_modules/**', '.DS_Store'],
          ext: 'js',
          watch: ['index.js'],
          delayTime: 1,
          cwd: __dirname,
        },
      },
    },
    watch: {
      js: {
        files: ['*.js', 'client/controllers/**/*.js', 'client/services/**/*.js', '!client/app.js'],
        tasks: ['eslint'],
        options: {
          livereload: true,
        },
      },
      html: {
        files: ['client/**/*.html', '!client/libs/**'],
        options: {
          livereload: true,
        },
      },
    },
    'gh-pages': {
      options: {
        base: 'client',
      },
      src: ['**'],
    },
  });

  // Load NPM tasks
  require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

  // Making grunt default to force in order not to break the project.
  grunt.option('force', true);

  // Default task(s).
  grunt.registerTask('default', ['eslint', 'prettify', 'concurrent']);
};
